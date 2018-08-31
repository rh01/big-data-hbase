/*
 * Copyright 2018 @rh01 https://github.com/rh01
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.readailib.hadoop.chapter1.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
 * @program: hadoop
 * @description: mapper
 * @Author: ReadAILib
 * @create: 2018-08-31 08:45
 **/
public class SecondarySortingTemperatureMapper extends Mapper<LongWritable, Text, DateTemperaturePair, Text> {
    private final Text theTemperature = new Text();
    private final DateTemperaturePair pair = new DateTemperaturePair();

    @Override
    /**
     * @param key is generated by hadoop
     * @param value has format : "YYYY,MM,DD,Temperature"
     */
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] tokens = value.toString().split(",");
        // YYYY = tokens[0]
        // MM = tokens[1]
        // DD = tokens[2]
        // temperature = tokens[3]
        String yearMonth = tokens[0] + tokens[1];
        String day = tokens[2];
        int temperature = Integer.parseInt(tokens[3]);
        // 准备规约器
        // DateTemperaturePair reducerKey = new DateTemperaturePair();
        pair.setYearMonth(yearMonth);
        pair.setDay(day);
        pair.setTemperature(temperature);

        theTemperature.set(tokens[3]);


        // 发送到reducer
        context.write(pair,  theTemperature);
    }
}