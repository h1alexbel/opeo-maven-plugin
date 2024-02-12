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
import java.util.Arrays;
import java.util.List;
import org.objectweb.asm.Opcodes;
import org.xembly.Directive;
import org.xembly.Directives;

/**
 * Static invocation ast node.
 * @since 0.1
 */
public final class StaticInvocation implements AstNode {

    /**
     * Method attributes.
     */
    private final Attributes attributes;

    /**
     * Arguments.
     */
    private final List<AstNode> arguments;

    /**
     * Constructor.
     * @param owner Owner class name
     * @param name Method name
     * @param descriptor Method descriptor
     * @param arguments Arguments
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public StaticInvocation(
        final String owner,
        final String name,
        final String descriptor,
        final AstNode... arguments
    ) {
        this(owner, name, descriptor, Arrays.asList(arguments));
    }

    /**
     * Constructor.
     * @param owner Owner class name
     * @param name Method name
     * @param descriptor Method descriptor
     * @param arguments Arguments
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public StaticInvocation(
        final String owner,
        final String name,
        final String descriptor,
        final List<AstNode> arguments
    ) {
        this(
            new Attributes().owner(owner).name(name).descriptor(descriptor).type("static"),
            arguments
        );
    }

    /**
     * Constructor.
     * @param attributes Attributes
     * @param arguments Arguments
     */
    public StaticInvocation(final Attributes attributes, final List<AstNode> arguments) {
        this.attributes = attributes;
        this.arguments = arguments;
    }

    @Override
    public Iterable<Directive> toXmir() {
        final Directives directives = new Directives();
        directives.add("o")
            .attr("base", String.format(".%s", this.attributes.name()))
            .attr("scope", this.attributes);
        this.arguments.stream().map(AstNode::toXmir).forEach(directives::append);
        return directives.up();
    }

    @Override
    public List<AstNode> opcodes() {
        final List<AstNode> res = new ArrayList<>(0);
        this.arguments.stream().map(AstNode::opcodes).forEach(res::addAll);
        res.add(
            new Opcode(
                Opcodes.INVOKESTATIC,
                this.attributes.owner(),
                this.attributes.name(),
                this.attributes.descriptor()
            )
        );
        return res;
    }
}
