/*
 * Copyright (c) 2015, University of Oslo
 *
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.hisp.dhis.android.sdk.core.persistence.models.interpretation;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.hisp.dhis.android.sdk.core.persistence.models.flow.InterpretationComment$Flow;
import org.hisp.dhis.android.sdk.core.persistence.models.flow.InterpretationComment$Flow$Table;
import org.hisp.dhis.android.sdk.models.common.meta.State;
import org.hisp.dhis.android.sdk.models.interpretation.IInterpretationCommentStore;
import org.hisp.dhis.android.sdk.models.interpretation.Interpretation;
import org.hisp.dhis.android.sdk.models.interpretation.InterpretationComment;

import java.util.List;

public class InterpretationCommentStore implements IInterpretationCommentStore {

    @Override
    public void insert(InterpretationComment object) {
        InterpretationComment$Flow commentFlow =
                InterpretationComment$Flow.fromModel(object);
        commentFlow.insert();

        object.setId(commentFlow.getId());
    }

    @Override
    public void update(InterpretationComment object) {
        InterpretationComment$Flow.fromModel(object).update();
    }

    @Override
    public void save(InterpretationComment object) {
        InterpretationComment$Flow commentFlow =
                InterpretationComment$Flow.fromModel(object);
        commentFlow.save();

        object.setId(commentFlow.getId());
    }

    @Override
    public void delete(InterpretationComment object) {
        InterpretationComment$Flow.fromModel(object).delete();
    }

    @Override
    public List<InterpretationComment> query() {
        List<InterpretationComment$Flow> commentFlows = new Select()
                .from(InterpretationComment$Flow.class)
                .queryList();
        return InterpretationComment$Flow.toModels(commentFlows);
    }

    @Override
    public InterpretationComment query(long id) {
        InterpretationComment$Flow commentFlow = new Select()
                .from(InterpretationComment$Flow.class)
                .where(Condition.column(InterpretationComment$Flow$Table.ID).is(id))
                .querySingle();
        return InterpretationComment$Flow.toModel(commentFlow);
    }

    @Override
    public InterpretationComment query(String uid) {
        InterpretationComment$Flow commentFlow = new Select()
                .from(InterpretationComment$Flow.class)
                .where(Condition.column(InterpretationComment$Flow$Table.UID).is(uid))
                .querySingle();
        return InterpretationComment$Flow.toModel(commentFlow);
    }

    @Override
    public List<InterpretationComment> filter(State state) {
        List<InterpretationComment$Flow> commentFlows = new Select()
                .from(InterpretationComment$Flow.class)
                .where(Condition.column(InterpretationComment$Flow$Table
                        .STATE).isNot(state.toString()))
                .queryList();
        return InterpretationComment$Flow.toModels(commentFlows);
    }

    @Override
    public List<InterpretationComment> filter(Interpretation interpretation, State state) {
        List<InterpretationComment$Flow> commentFlows = new Select()
                .from(InterpretationComment$Flow.class)
                .where(Condition.column(InterpretationComment$Flow$Table
                        .INTERPRETATION_INTERPRETATION).is(interpretation.getId()))
                .and(Condition.column(InterpretationComment$Flow$Table
                        .STATE).isNot(state.toString()))
                .queryList();
        return InterpretationComment$Flow.toModels(commentFlows);
    }
}
