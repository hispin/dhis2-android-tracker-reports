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

package org.hisp.dhis.android.sdk.core.persistence.models.flow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.Table;

import org.hisp.dhis.android.sdk.core.persistence.models.common.meta.DbDhis;
import org.hisp.dhis.android.sdk.models.common.meta.State;
import org.hisp.dhis.android.sdk.models.dashboard.DashboardElement;

import java.util.ArrayList;
import java.util.List;

@Table(databaseName = DbDhis.NAME)
public final class DashboardElement$Flow extends BaseIdentifiableObject$Flow {
    static final String DASHBOARD_ITEM_KEY = "dashboardItem";

    @Column
    @NotNull
    State state;

    @Column
    @NotNull
    @ForeignKey(
            references = {
                    @ForeignKeyReference(columnName = DASHBOARD_ITEM_KEY, columnType = long.class, foreignColumnName = "id")
            }, saveForeignKeyModel = false, onDelete = ForeignKeyAction.CASCADE
    )
    DashboardItem$Flow dashboardItem;

    public DashboardElement$Flow() {
        state = State.SYNCED;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public DashboardItem$Flow getDashboardItem() {
        return dashboardItem;
    }

    public void setDashboardItem(DashboardItem$Flow dashboardItem) {
        this.dashboardItem = dashboardItem;
    }

    public static DashboardElement$Flow fromModel(DashboardElement dashboardElement) {
        if (dashboardElement == null) {
            return null;
        }

        DashboardElement$Flow dashboardElementFlow = new DashboardElement$Flow();
        dashboardElementFlow.setId(dashboardElement.getId());
        dashboardElementFlow.setUId(dashboardElement.getUId());
        dashboardElementFlow.setCreated(dashboardElement.getCreated());
        dashboardElementFlow.setLastUpdated(dashboardElement.getLastUpdated());
        dashboardElementFlow.setAccess(dashboardElement.getAccess());
        dashboardElementFlow.setName(dashboardElement.getName());
        dashboardElementFlow.setDisplayName(dashboardElement.getDisplayName());
        dashboardElementFlow.setDashboardItem(DashboardItem$Flow
                .fromModel(dashboardElement.getDashboardItem()));
        dashboardElementFlow.setState(dashboardElement.getState());
        return dashboardElementFlow;
    }

    public static DashboardElement toModel(DashboardElement$Flow dashboardElementFlow) {
        if (dashboardElementFlow == null) {
            return null;
        }

        DashboardElement dashboardElement = new DashboardElement();
        dashboardElement.setId(dashboardElementFlow.getId());
        dashboardElement.setUId(dashboardElementFlow.getUId());
        dashboardElement.setCreated(dashboardElementFlow.getCreated());
        dashboardElement.setLastUpdated(dashboardElementFlow.getLastUpdated());
        dashboardElement.setAccess(dashboardElementFlow.getAccess());
        dashboardElement.setName(dashboardElementFlow.getName());
        dashboardElement.setDisplayName(dashboardElementFlow.getDisplayName());
        dashboardElement.setDashboardItem(DashboardItem$Flow
                .toModel(dashboardElementFlow.getDashboardItem()));
        dashboardElement.setState(dashboardElementFlow.getState());
        return dashboardElement;
    }

    public static List<DashboardElement> toModels(List<DashboardElement$Flow> elementFlows) {
        List<DashboardElement> dashboardElements = new ArrayList<>();

        if (elementFlows != null && !elementFlows.isEmpty()) {
            for (DashboardElement$Flow dashboardElementFlow : elementFlows) {
                dashboardElements.add(toModel(dashboardElementFlow));
            }
        }

        return dashboardElements;
    }
}