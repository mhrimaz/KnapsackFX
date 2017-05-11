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

import com.mhrimaz.model.BinarySolution;
import com.mhrimaz.model.Item;
import com.mhrimaz.model.KnapsackData;
import com.mhrimaz.model.Solution;
import com.mhrimaz.solver.HillClimbingStrategy;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author mhrimaz
 */
public class HCStochasticStrategy extends HillClimbingStrategy {
    
    private final Random random = new Random(System.nanoTime());
    private final int iterationLimit;

    public HCStochasticStrategy(int iterationLimit) {
        this.iterationLimit = iterationLimit;
    }

    @Override
    public Solution solve(KnapsackData data) {
        this.data = data;

        long start = System.currentTimeMillis();
        BinarySolution solution = new BinarySolution(data.getSize());

        solution.shuffle();
        for (int i = 0; i < iterationLimit; i++) {
            solution = getNextState(solution);
        }

        long end = System.currentTimeMillis();
        List<Item> pickedItem = generateSolution(data, solution);
        return new Solution(pickedItem, end - start);
    }

    /**
     *
     * @param current
     * @return
     */
    @Override
    protected BinarySolution getNextState(BinarySolution current) {
        BinarySolution newSolution = current;
        List<BinarySolution> betterNeighbors = new LinkedList<>();
        for (int i = 0; i < current.getSize(); i++) {
            BinarySolution mutated = new BinarySolution(current);
            mutated.flip(i);
            mutated.updateFitness(data, ALPHA);
            if (mutated.getFitness() <= newSolution.getFitness()) {
                betterNeighbors.add(mutated);
            }
        }
        if (betterNeighbors.isEmpty()) {
            return current;
        }
        return betterNeighbors.get(random.nextInt(betterNeighbors.size()));
    }
}
