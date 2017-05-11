/*
 * The MIT License
 *
 * Copyright 2017 Hossein Rimaz.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mhrimaz.solver;

import com.mhrimaz.model.BinarySolution;
import com.mhrimaz.model.Item;
import com.mhrimaz.model.KnapsackData;
import com.mhrimaz.model.Solution;
import java.util.List;

/**
 *
 * @author mhrimaz
 */
public abstract class HillClimbingStrategy implements KnapsackStrategy {

    protected final static double ALPHA = 1000000;
    protected KnapsackData data;

    @Override
    public Solution solve(KnapsackData data) {
        this.data = data;

        long start = System.currentTimeMillis();
        BinarySolution solution = new BinarySolution(data.getSize());

        solution.shuffle();
        while (true) {
            BinarySolution newSolution = getNextState(solution);
            if (newSolution == solution) {
                break;
            }
            solution = newSolution;
        }

        long end = System.currentTimeMillis();
        List<Item> pickedItem = generateSolution(data, solution);
        return new Solution(pickedItem, end - start);
    }

    protected abstract BinarySolution getNextState(BinarySolution current);
}
