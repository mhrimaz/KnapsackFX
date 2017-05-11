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
package com.mhrimaz.model;

import java.util.Arrays;

/**
 *
 * @author mhrimaz
 */
public final class BinarySolution {

    private final byte[] chromosome;
    private double fitness;
    private long weight;

    public BinarySolution(int size) {
        chromosome = new byte[size];
        fitness = Double.MAX_VALUE;
        weight = Long.MAX_VALUE;
    }

    public BinarySolution(BinarySolution other) {
        this.chromosome = other.chromosome.clone();
        fitness = other.getFitness();
        weight = other.getWeight();
    }

    public byte getBit(int position) {
        return chromosome[position];
    }

    public double getFitness() {
        return fitness;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void flip(int position) {
        chromosome[position] = (byte) (chromosome[position] ^ 1);
    }

    public void shuffle() {
        for (int i = 0; i < chromosome.length; i++) {
            if (Math.random() > 0.3) {
                chromosome[i] = 0;
            } else {
                chromosome[i] = 1;
            }
        }
    }

    public int getSize() {
        return chromosome.length;
    }

    public void updateFitness(KnapsackData data, double alpha) {
        long sumVal = 0, sumWeight = 0;
        for (int i = 0; i < data.getSize(); i++) {
            Item item = data.getData(i);
            if (getBit(i) == 1) {
                sumWeight += item.getWeight();
            } else {
                sumVal += item.getValue();
            }
        }
        double violation = Math.max((double) sumWeight / data.getMaximumWeight() - 1, 0);
        setWeight(sumWeight);
        setFitness(sumVal + alpha * violation);
    }

    @Override
    public String toString() {
        return Arrays.toString(chromosome);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Arrays.hashCode(this.chromosome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BinarySolution other = (BinarySolution) obj;
        return Arrays.equals(this.chromosome, other.chromosome);
    }

}
