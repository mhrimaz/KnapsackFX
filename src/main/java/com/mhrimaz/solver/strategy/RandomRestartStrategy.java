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
package com.mhrimaz.solver.strategy;

import com.mhrimaz.model.KnapsackData;
import com.mhrimaz.model.Solution;
import com.mhrimaz.solver.HillClimbingStrategy;
import com.mhrimaz.solver.KnapsackStrategy;

/**
 *
 * @author mhrimaz
 */
public class RandomRestartStrategy implements KnapsackStrategy {

    private final HillClimbingStrategy strategy;
    private final int retryLimit;

    public RandomRestartStrategy(HillClimbingStrategy strategy, int retryLimit) {
        if (strategy.getClass().equals(this.getClass())) {
            throw new IllegalStateException("Cannot instantiate Random Restart Strategy with Random Restart");
        }
        this.strategy = strategy;
        this.retryLimit = retryLimit;
    }

    @Override
    public Solution solve(KnapsackData data) {
        long start = System.currentTimeMillis();
        Solution best = strategy.solve(data);
        for (int i = 0; i < retryLimit; i++) {
            Solution solution = strategy.solve(data);
            if (solution.getGainedValue() >= best.getGainedValue()) {
                best = solution;
            }
        }
        long end = System.currentTimeMillis();
        best.setTakenTime(end - start);
        return best;
    }
}
