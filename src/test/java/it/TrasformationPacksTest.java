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
package it;

import org.eolang.jucs.ClasspathSource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * Integration tests that verify that java code transforms into EO correctly.
 * The test logic is as follows:
 * 1. Compile java code into bytecode
 * 2. Transform bytecode into XMIR
 * 3. Compile expected EO into XMIR
 * 4. Compare XMIRs
 * @since 0.1
 */
final class TrasformationPacksTest {

    @ParameterizedTest
    @ClasspathSource(value = "packs", glob = "**.yaml")
    void checksPack(final String pack) {
        //@checkstyle MethodBodyCommentsCheck (10 lines)
        // @todo #6:90min Implement primitive transformation test.
        //  Currently we just get the pack name from the test and check that it is not null.
        //  We have to implement proper transformation test. It will consist of the following steps:
        //  1. Compile java code into bytecode
        //  2. Transform bytecode into XMIR
        //  3. Compile expected EO into XMIR
        //  4. Compare XMIRs
        MatcherAssert.assertThat(
            "Pack is not null",
            pack,
            Matchers.notNullValue()
        );
    }
}
