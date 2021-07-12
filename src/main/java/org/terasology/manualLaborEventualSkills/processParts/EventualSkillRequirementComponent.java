// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.manualLaborEventualSkills.processParts;

import org.terasology.gestalt.entitysystem.component.Component;

public class EventualSkillRequirementComponent implements Component<EventualSkillRequirementComponent> {
    public String skillUrn;
    public int level;

    @Override
    public void copy(EventualSkillRequirementComponent other) {
        this.skillUrn = other.skillUrn;
        this.level = other.level;
    }
}
