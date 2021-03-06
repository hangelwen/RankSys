/*
 * Copyright (C) 2016 RankSys http://ranksys.org
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.ranksys.formats.preference;

import static es.uam.eps.ir.ranksys.core.util.FastStringSplitter.split;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Double.parseDouble;
import java.util.stream.Stream;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;
import org.ranksys.formats.parsing.Parser;

/**
 * Reads a file of tab-separated user-item-rating triples, one per line.
 *
 * @author Saúl Vargas (Saul@VargasSandoval.es)
 */
public class SimpleRatingPreferencesReader implements PreferencesReader {

    /**
     * Returns and instance of this class.
     *
     * @param <U> user type
     * @param <I> item type
     * @return an instance of SimpleRatingPreferencesReader
     */
    public static <U, I> SimpleRatingPreferencesReader get() {
        return new SimpleRatingPreferencesReader();
    }

    private SimpleRatingPreferencesReader() {
    }

    @Override
    public <U, I> Stream<Tuple3<U, I, Double>> read(InputStream in, Parser<U> up, Parser<I> ip) {
        return new BufferedReader(new InputStreamReader(in)).lines().map(line -> {
            CharSequence[] tokens = split(line, '\t', 4);
            U user = up.parse(tokens[0]);
            I item = ip.parse(tokens[1]);
            double value = parseDouble(tokens[2].toString());

            return Tuple.tuple(user, item, value);
        });
    }

}
