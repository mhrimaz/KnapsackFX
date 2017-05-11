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

import com.mhrimaz.model.Item;
import com.mhrimaz.model.KnapsackData;
import com.mhrimaz.model.Solution;
import java.util.List;

/**
 *
 * @author mhrimaz
 */
public class KnapsackSolver {

    private final KnapsackData data;
    private Solution bestSolution;
    private KnapsackStrategy strategy;

    public KnapsackSolver(KnapsackData data, KnapsackStrategy strategy) {
        this.data = data;
        this.strategy = strategy;
    }

    private Solution find() {
        return strategy.solve(data);
    }

    /**
     * for stochastic algorithms the output of the best solution is different
     *
     * @return best founded solution
     */
    public Solution getSolution() {
        if (bestSolution == null) {
            bestSolution = find();
        }
        return bestSolution;
    }

    public long getTakenTime() {
        return getSolution().getTakenTime();
    }

    public List<Item> getSelectedItem() {
        return getSolution().getPickedItem();
    }

}
