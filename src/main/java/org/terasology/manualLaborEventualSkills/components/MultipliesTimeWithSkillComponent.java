// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.manualLaborEventualSkills.components;

import org.terasology.gestalt.entitysystem.component.Component;

public class MultipliesTimeWithSkillComponent implements Component<MultipliesTimeWithSkillComponent> {
    public String skillUrn;
    public float multiplier;

    @Override
    public void copyFrom(MultipliesTimeWithSkillComponent other) {
        this.multiplier = other.multiplier;
        this.skillUrn = other.skillUrn;
    }
}
