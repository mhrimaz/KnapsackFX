/*
 * The MIT License
 *
 * Copyright 2017 Your Organisation.
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
import com.mhrimaz.solver.KnapsackStrategy;
import java.util.List;
import java.util.Random;

/**
 * Simulated Annealing Strategy to solve knapsack problem
 *
 * @author mhrimaz
 */
public class SAStrategy implements KnapsackStrategy {

    private double temperature;
    private final double coolingFactor;
    private final double endingTempreture;
    private final int samplingSize;
    private static final double ALPHA = 1000000;
    private final Random random = new Random(System.nanoTime());
    private KnapsackData data;

    public SAStrategy(int samplingSize, double initTemerature, double endingTempreture, double coolingFactor) {
        this.samplingSize = samplingSize;
        this.endingTempreture = endingTempreture;
        this.coolingFactor = coolingFactor;
        this.temperature = initTemerature;
    }

    @Override
    public Solution solve(KnapsackData data) {

        this.data = data;

        long start = System.currentTimeMillis();
        BinarySolution current = new BinarySolution(data.getSize());
        BinarySolution best = current;
        current.updateFitness(data, ALPHA);

        while (temperature > endingTempreture) {
            for (int m = 0; m < samplingSize; m++) {
                current = getNextState(current);
                if (current.getFitness() < best.getFitness()) {
                    best = current;
                }
            }
            cool();
        }

        long end = System.currentTimeMillis();
        List<Item> pickedItem = generateSolution(data, best);
        return new Solution(pickedItem, end - start);
    }

    private BinarySolution getNextState(BinarySolution current) {
        BinarySolution newSolution = getNeighbour(current);
        double delta = newSolution.getFitness() - current.getFitness();
        if (delta < 0) {
            return newSolution;
        } else {
            double x = Math.random();
            if (x < Math.exp(-delta / temperature)) {
                return newSolution;
            } else {
                return current;
            }
        }
    }

    private BinarySolution getNeighbour(BinarySolution current) {
        BinarySolution mutated = new BinarySolution(current);
        int x = random.nextInt(current.getSize());
        mutated.flip(x);
        mutated.updateFitness(data, ALPHA);
        return mutated;
    }

    private void cool() {
        temperature *= coolingFactor;
    }
}
