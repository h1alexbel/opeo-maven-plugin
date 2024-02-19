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
package org.eolang.opeo.decompilation.handlers;

import java.util.List;
import org.eolang.opeo.ast.AstNode;
import org.eolang.opeo.ast.Attributes;
import org.eolang.opeo.ast.Constructor;
import org.eolang.opeo.ast.Reference;
import org.eolang.opeo.ast.Super;
import org.eolang.opeo.decompilation.InstructionHandler;
import org.eolang.opeo.decompilation.MachineState;
import org.objectweb.asm.Type;

/**
 * Invokespecial instruction handler.
 * @since 0.1
 */
public class InvokespecialHandler implements InstructionHandler {
    @Override
    public void handle(final MachineState state) {
        if (!state.operand(1).equals("<init>")) {
            throw new UnsupportedOperationException(
                String.format("Instruction %s is not supported yet", state)
            );
        }
        final String descriptor = (String) state.operand(2);
        final List<AstNode> args = state.stack().pop(
            Type.getArgumentCount(descriptor)
        );
        final String target = (String) state.operand(0);
        //@checkstyle MethodBodyCommentsCheck (10 lines)
        // @todo #76:90min Target might not be an Object.
        //  Here we just compare with object, but if the current class has a parent, the
        //  target might not be an Object. We should compare with the current class name
        //  instead. Moreover, we have to pass the 'target' as an argument to the
        //  constructor of the 'Super' class somehow.
        if ("java/lang/Object".equals(target)) {
            state.stack().push(
                new Super(state.stack().pop(), args, descriptor)
            );
        } else {
            ((Reference) state.stack().pop())
                .link(new Constructor(target, new Attributes().descriptor(descriptor), args));
        }
    }

}
