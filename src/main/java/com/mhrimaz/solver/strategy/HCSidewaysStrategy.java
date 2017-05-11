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
import com.mhrimaz.solver.HillClimbingStrategy;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author mhrimaz
 */
public class HCSidewaysStrategy extends HillClimbingStrategy {

    private final int sidewaysMovesLimit;
    private int sidewaysStepTaken;
    private final Random random = new Random(System.nanoTime());

    public HCSidewaysStrategy(int sidewaysMovesLimit) {
        this.sidewaysMovesLimit = sidewaysMovesLimit;
    }

    /**
     *
     * @param current
     * @return
     */
    @Override
    protected BinarySolution getNextState(BinarySolution current) {
        BinarySolution newSolution = current;
        boolean betterNeighborFounded = false;
        List<BinarySolution> equalNeighbors = new LinkedList<>();
        for (int i = 0; i < current.getSize(); i++) {
            BinarySolution mutated = new BinarySolution(current);
            mutated.flip(i);
            mutated.updateFitness(data, ALPHA);
            if (mutated.getFitness() < newSolution.getFitness()) {
                newSolution = mutated;
                sidewaysStepTaken = 0;
                betterNeighborFounded = true;
            } else if (mutated.getFitness() == newSolution.getFitness() && !betterNeighborFounded) {
                equalNeighbors.add(mutated);
            }
        }
        if (betterNeighborFounded || equalNeighbors.isEmpty() || sidewaysStepTaken > sidewaysMovesLimit) {
            return newSolution;
        } else {
            sidewaysStepTaken++;
            return equalNeighbors.get(random.nextInt(equalNeighbors.size()));
        }
    }

}
