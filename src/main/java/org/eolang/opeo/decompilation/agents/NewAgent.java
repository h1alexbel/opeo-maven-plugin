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
package org.eolang.opeo.decompilation.agents;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.eolang.opeo.ast.NewAddress;
import org.eolang.opeo.decompilation.DecompilationAgent;
import org.eolang.opeo.decompilation.DecompilerState;
import org.objectweb.asm.Opcodes;

/**
 * New instruction handler.
 * @since 0.1
 */
public final class NewAgent implements DecompilationAgent {

    /**
     * Supported opcodes.
     */
    private static final Set<Integer> SUPPORTED = new HashSet<>(
        Arrays.asList(
            Opcodes.NEW
        )
    );

    @Override
    public void handle(final DecompilerState state) {
        if (NewAgent.SUPPORTED.contains(state.instruction().opcode())) {
            state.stack().push(new NewAddress(state.operand(0).toString()));
            state.popInstruction();
        }
    }

}
