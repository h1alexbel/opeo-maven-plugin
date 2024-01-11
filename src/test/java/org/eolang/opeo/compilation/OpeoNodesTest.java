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
package org.eolang.opeo.compilation;

import java.util.List;
import org.eolang.jeo.representation.xmir.XmlNode;
import org.eolang.opeo.ast.Opcode;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

/**
 * Tests for {@link OpeoNodes}.
 * @since 0.1
 */
final class OpeoNodesTest {

    @Test
    void convertsOpcodesAsIs() {
        final List<XmlNode> nodes = new OpeoNodes(
            new Opcode(Opcodes.ICONST_0, false), new Opcode(Opcodes.POP, false)
        ).toJeoNodes();
        MatcherAssert.assertThat(
            "We expect the first node to be ICONST_0",
            nodes.get(0).toString(),
            Matchers.equalTo(
                "<o base=\"opcode\" name=\"ICONST_0\">\n   <o base=\"int\" data=\"bytes\">00 00 00 00 00 00 00 03</o>\n</o>\n"
            )
        );
        MatcherAssert.assertThat(
            "We expect the second node to be POP",
            nodes.get(1).toString(),
            Matchers.equalTo(
                "<o base=\"opcode\" name=\"POP\">\n   <o base=\"int\" data=\"bytes\">00 00 00 00 00 00 00 57</o>\n</o>\n"
            )
        );
    }
}