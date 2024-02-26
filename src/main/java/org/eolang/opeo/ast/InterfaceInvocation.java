/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2023 Objectionary.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.eolang.opeo.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.ToString;
import org.eolang.jeo.representation.xmir.XmlNode;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.xembly.Directive;
import org.xembly.Directives;

/**
 * Interface invocation.
 * @since 0.2
 */
@ToString
public final class InterfaceInvocation implements AstNode, Typed {
    private final AstNode source;
    private final Attributes attrs;
    private final List<AstNode> arguments;

    public InterfaceInvocation(final XmlNode node, final Function<XmlNode, AstNode> parser) {
        this(
            InterfaceInvocation.xsource(node, parser),
            InterfaceInvocation.xattrs(node),
            InterfaceInvocation.xargs(node, parser)
        );
    }

    private static AstNode xsource(final XmlNode node, final Function<XmlNode, AstNode> parser) {
        final List<XmlNode> inner = node.children().collect(Collectors.toList());
        return parser.apply(inner.get(0));
    }

    public InterfaceInvocation(
        final AstNode source,
        final Attributes attributes,
        final List<AstNode> args
    ) {
        this.source = source;
        this.attrs = attributes.type("interface");
        this.arguments = args;
    }

    @Override
    public List<AstNode> opcodes() {
        final List<AstNode> res = new ArrayList<>(0);
        res.addAll(this.source.opcodes());
        this.arguments.stream().map(AstNode::opcodes).forEach(res::addAll);
        if (!(this.source instanceof Typed)) {
            throw new IllegalArgumentException(
                String.format(
                    "Source must be of type Typed, but it is %s. Most probably, we don't implement the type of the source yet.",
                    this.source
                )
            );
        }
        final Typed owner = (Typed) this.source;
        res.add(
            new Opcode(
                Opcodes.INVOKEINTERFACE,
                owner.type().getClassName().replace('.', '/'),
                this.attrs.name(),
                this.attrs.descriptor()
            )
        );
        return res;
    }

    @Override
    public Iterable<Directive> toXmir() {
        if (Objects.isNull(this.source)) {
            throw new IllegalArgumentException(
                String.format(
                    "Source and method must not be null, but they are %s",
                    this
                )
            );
        }
        final Directives directives = new Directives();
        directives.add("o")
            .attr("base", String.format(".%s", this.attrs.name()))
            .attr("scope", this.attrs)
            .append(this.source.toXmir());
        this.arguments.stream().map(AstNode::toXmir).forEach(directives::append);
        return directives.up();
    }

    @Override
    public Type type() {
        return Type.getReturnType(this.attrs.descriptor());
    }

    /**
     * Extracts attributes from the node.
     * @param node XML node
     * @return Attributes
     */
    private static Attributes xattrs(final XmlNode node) {
        return node.attribute("scope").map(Attributes::new).orElseThrow(
            () -> new IllegalArgumentException(
                String.format(
                    "Can't retrieve interface invocation attributes from the node %s",
                    node
                )
            )
        );
    }

    /**
     * Convert XML nodes into a list of arguments.
     * @param node XML node.
     * @param parser Parser.
     * @return List of arguments.
     */
    private static List<AstNode> xargs(final XmlNode node, Function<XmlNode, AstNode> parser) {
        final List<XmlNode> all = node.children().collect(Collectors.toList());
        final List<AstNode> arguments;
        if (all.size() > 1) {
            arguments = all.subList(1, all.size())
                .stream()
                .map(parser::apply)
                .collect(Collectors.toList());
        } else {
            arguments = Collections.emptyList();
        }
        return arguments;
    }
}
