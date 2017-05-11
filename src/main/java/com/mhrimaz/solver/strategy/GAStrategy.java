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

import com.mhrimaz.model.Item;
import com.mhrimaz.model.KnapsackData;
import com.mhrimaz.model.Solution;
import com.mhrimaz.solver.KnapsackStrategy;
import java.util.List;
import java.util.stream.Collectors;
import org.jenetics.BitChromosome;
import org.jenetics.BitGene;
import org.jenetics.Genotype;
import org.jenetics.Mutator;
import org.jenetics.UniformCrossover;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.EvolutionStatistics;
import static org.jenetics.engine.limit.bySteadyFitness;

/**
 * Genetic Algorithm strategy to solve Knapsack 0/1 problem
 *
 * @author hossein
 */
public class GAStrategy implements KnapsackStrategy {
    private final static double ALPHA = 1000000;

    private final double mutationRate;
    private final double recombinationRate;
    private final int populationSize;

    public GAStrategy(double mutationRate, double recombinationRate, int populationSize) {
        this.mutationRate = mutationRate;
        this.recombinationRate = recombinationRate;
        this.populationSize = populationSize;
    }

    @Override
    public Solution solve(KnapsackData data) {
        final Engine<BitGene, Double> engine = Engine
                .builder((Genotype<BitGene> t) -> {
                    BitChromosome chromosome = (BitChromosome) t.getChromosome();
                    double sum = chromosome.zeros()
                            .mapToDouble(i -> data.getData(i).getValue())
                            .sum();
                    double weight = chromosome.ones()
                            .mapToDouble(i -> data.getData(i).getWeight())
                            .sum();
                    double violation = Math.max(weight / data.getMaximumWeight() - 1, 0);
                    return sum + ALPHA * violation;
                }, BitChromosome.of(data.getSize()))
                .populationSize(populationSize)
                .offspringFraction(0.8)
                .alterers(
                        new Mutator<>(mutationRate),
                        new UniformCrossover<>(recombinationRate))
                .minimizing()
                .build();

        // Create evolution statistics consumer.
        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

        long start = System.currentTimeMillis();
        Genotype<BitGene> best = engine.stream()
                .limit(bySteadyFitness(50))
                .peek(statistics)
                .collect(EvolutionResult.toBestGenotype());
        System.out.println("statistics ============\n" + statistics);
        BitChromosome chromosome = (BitChromosome) best.getChromosome();
        List<Item> pickedItem = chromosome.ones().parallel()
                .mapToObj(i -> data.getData(i)).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        return new Solution(pickedItem, end - start);
    }

}
