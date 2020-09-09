// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.manualLaborEventualSkills;

import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.event.ReceiveEvent;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.logic.characters.GetMaxSpeedEvent;
import org.terasology.eventualSkills.components.EntitySkillsComponent;
import org.terasology.gestalt.assets.ResourceUrn;

@RegisterSystem
public class MovementModifyingSkillsSystem extends BaseComponentSystem {
    @ReceiveEvent
    public void modifySpeed(GetMaxSpeedEvent event, EntityRef entityRef, EntitySkillsComponent skillsComponent) {
        switch (event.getMovementMode()) {
            case CLIMBING:
            case SWIMMING:
            case WALKING:
                event.multiply(1f + (0.05f * skillsComponent.getSkillLevel(new ResourceUrn("ManualLaborEventualSkills" +
                        ":FitnessSkill"))));
        }

        switch (event.getMovementMode()) {
            case SWIMMING:
                event.multiply(1f + (0.1f * skillsComponent.getSkillLevel(new ResourceUrn("ManualLaborEventualSkills" +
                        ":SwimmingSkill"))));
                break;
            case WALKING:
                event.multiply(1f + (0.1f * skillsComponent.getSkillLevel(new ResourceUrn("ManualLaborEventualSkills" +
                        ":RunningSkill"))));
                break;

        }
    }
}
