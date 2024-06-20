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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.eolang.jeo.representation.xmir.XmlNode;
import org.eolang.opeo.compilation.Parser;

/**
 * Arguments of an invocation.
 * @since 0.2
 */
public final class Arguments {

    /**
     * Root node.
     */
    private final XmlNode root;

    /**
     * Parser that understands how to parse subnodes.
     */
    private final Parser parser;

    /**
     * Begin index.
     * We start to parse arguments from this index.
     */
    private final int begin;

    /**
     * Constructor.
     * @param root Root node.
     * @param parser Parser that understands how to parse subnodes.
     * @param begin Begin index.
     */
    public Arguments(final XmlNode root, final Parser parser, final int begin) {
        this.root = root;
        this.parser = parser;
        this.begin = begin;
    }

    /**
     * Convert to list.
     * @return List of arguments.
     */
    public List<AstNode> toList() {
        final List<XmlNode> all = this.root.children().collect(Collectors.toList());
        final List<AstNode> arguments;
        if (all.size() > this.begin) {
            arguments = all.subList(this.begin, all.size())
                .stream()
                .map(this.parser::parse)
                .collect(Collectors.toList());
        } else {
            arguments = Collections.emptyList();
        }
        return arguments;
    }
}
