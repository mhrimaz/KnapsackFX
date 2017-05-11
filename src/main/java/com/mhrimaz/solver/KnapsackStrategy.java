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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mhrimaz
 */
@FunctionalInterface
public interface KnapsackStrategy {

    /**
     * solve the knapsack problem
     *
     * @param data Knapsack Data
     * @return best founded solution
     * @see com.mhrimaz.model.KnapsackData
     */
    public Solution solve(KnapsackData data);

    /**
     * generate list of items from the binary solution 
     * @param knapsackData Knapsack data
     * @param solution Binary solution of the problem
     * @return list of the selected items
     */
    public default List<Item> generateSolution(KnapsackData knapsackData, BinarySolution solution) {
        List<Item> pickedItem = new ArrayList<>();
        for (int i = 0; i < knapsackData.getSize(); i++) {
            if (solution.getBit(i) == 1) {
                pickedItem.add(knapsackData.getData(i));
            }
        }
        return pickedItem;
    }
}
