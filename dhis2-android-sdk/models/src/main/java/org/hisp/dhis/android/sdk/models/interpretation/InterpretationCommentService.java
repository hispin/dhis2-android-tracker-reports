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

package org.hisp.dhis.android.sdk.models.interpretation;

import org.hisp.dhis.android.sdk.models.common.meta.State;

public final class InterpretationCommentService implements IInterpretationCommentService {
    private final IInterpretationCommentStore interpretationCommentStore;

    public InterpretationCommentService(IInterpretationCommentStore interpretationCommentStore) {
        this.interpretationCommentStore = interpretationCommentStore;
    }

    /**
     * Performs soft delete of model. If State of object was SYNCED, it will be set to TO_DELETE.
     * If the model is persisted only in the local database, it will be removed immediately.
     */
    @Override
    public void deleteComment(InterpretationComment comment) {
        if (State.TO_POST.equals(comment.getState())) {
            interpretationCommentStore.delete(comment);
        } else {
            comment.setState(State.TO_DELETE);
            interpretationCommentStore.save(comment);
        }
    }

    /**
     * Method modifies the original comment text and sets TO_UPDATE as state,
     * if the object was received from server. If the model was persisted only locally,
     * the State will be the TO_POST.
     *
     * @param text Edited text of comment.
     */
    @Override
    public void updateCommentText(InterpretationComment comment, String text) {
        comment.setText(text);

        if (comment.getState() != State.TO_DELETE &&
                comment.getState() != State.TO_POST) {
            comment.setState(State.TO_UPDATE);
        }

        interpretationCommentStore.save(comment);
    }
}
