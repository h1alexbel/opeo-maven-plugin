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
import java.util.List;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.xembly.Directive;
import org.xembly.Directives;

/**
 * Substraction output node.
 * @since 0.1
 */
public final class Substraction implements AstNode, Typed {

    /**
     * Left operand.
     */
    private final AstNode left;

    /**
     * Right operand.
     */
    private final AstNode right;

    /**
     * Constructor.
     * @param left Left operand.
     * @param right Right operand.
     */
    public Substraction(final AstNode left, final AstNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Iterable<Directive> toXmir() {
        return new Directives().add("o")
            .attr("base", ".minus")
            .append(this.left.toXmir())
            .append(this.right.toXmir())
            .up();
    }

    @Override
    public List<AstNode> opcodes() {
        final List<AstNode> res = new ArrayList<>(0);
        res.addAll(this.left.opcodes());
        res.addAll(this.right.opcodes());
        res.add(this.opcode());
        return res;
    }

    @Override
    public Type type() {
        return new ExpressionType(this.left, this.right).type();
//        final Type result;
//        final Type ltype = this.cast(this.left).type();
//        final Type rtype = this.cast(this.right).type();
//        if (ltype.equals(Type.DOUBLE_TYPE) || rtype.equals(Type.DOUBLE_TYPE)) {
//            result = Type.DOUBLE_TYPE;
//        } else if (ltype.equals(Type.FLOAT_TYPE) || rtype.equals(Type.FLOAT_TYPE)) {
//            result = Type.FLOAT_TYPE;
//        } else if (ltype.equals(Type.LONG_TYPE) || rtype.equals(Type.LONG_TYPE)) {
//            result = Type.LONG_TYPE;
//        } else {
//            result = Type.INT_TYPE;
//        }
//        return result;
    }

    /**
     * Cast node to a typed node.
     * @param node Node to cast.
     * @return Typed node.
     */
    private Typed cast(final AstNode node) {
        final Typed result;
        if (node instanceof Typed) {
            result = (Typed) node;
        } else {
            throw new IllegalStateException(
                String.format("Node %s is not typed inside %s", node, this)
            );
        }
        return result;
    }

    /**
     * Convert string into an opcode.
     * @return Opcode.
     */
    private Opcode opcode() {
        final Opcode result;
        final Type type = this.type();
        if (type.equals(Type.LONG_TYPE)) {
            result = new Opcode(Opcodes.LSUB);
        } else if (type.equals(Type.FLOAT_TYPE)) {
            result = new Opcode(Opcodes.FSUB);
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            result = new Opcode(Opcodes.DSUB);
        } else {
            result = new Opcode(Opcodes.ISUB);
        }
        return result;
    }

}
