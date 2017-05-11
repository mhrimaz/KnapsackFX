package com.mhrimaz.solver.strategy;

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
import com.mhrimaz.model.Item;
import com.mhrimaz.model.KnapsackData;
import com.mhrimaz.model.Solution;
import com.mhrimaz.solver.KnapsackStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic Programming Strategy for solving Knapsack 0/1 problem
 * @author mhrimaz
 */
public class DPStrategy implements KnapsackStrategy {

    @Override
    public Solution solve(KnapsackData data) {
        long start = System.currentTimeMillis();
        int[][] opt = new int[data.getSize()][data.getMaximumWeight() + 1];
        boolean[][] sol = new boolean[data.getSize()][data.getMaximumWeight() + 1];

        for (int n = 0; n < data.getSize(); n++) {
            for (int w = 0; w <= data.getMaximumWeight(); w++) {
                // don't take item n
                int option1 = 0;
                if (n - 1 >= 0) {
                    option1 = opt[n - 1][w];
                }
                // take item n
                int option2 = Integer.MIN_VALUE;
                if (data.getData(n).getWeight() <= w) {
                    option2 = data.getData(n).getValue() + (n - 1 >= 0 ? opt[n - 1][w - data.getData(n).getWeight()] : 0);
                }
                // select better of two options
                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = option2 > option1;
            }
        }

        List<Item> pickedItem = new ArrayList<>();
        for (int n = data.getSize() - 1, w = data.getMaximumWeight(); n >= 0; n--) {
            if (sol[n][w]) {
                pickedItem.add(data.getData(n));
                w = w - data.getData(n).getWeight();
            }
        }
        long end = System.currentTimeMillis();
        return new Solution(pickedItem, end - start);
    }

}
