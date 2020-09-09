// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.manualLaborEventualSkills;

import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.event.ReceiveEvent;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.eventualSkills.components.EntitySkillsComponent;
import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.manualLaborEventualSkills.components.MultipliesTimeWithSkillComponent;
import org.terasology.workstation.processPart.ProcessEntityGetDurationEvent;

@RegisterSystem
public class ProcessTimeSkillModifiersSystem extends BaseComponentSystem {
    @ReceiveEvent
    public void onModifyProcessingTime(ProcessEntityGetDurationEvent event, EntityRef processEntity,
                                       MultipliesTimeWithSkillComponent multipliesTimeWithSkillComponent) {
        EntitySkillsComponent skillsComponent = event.getInstigator().getComponent(EntitySkillsComponent.class);
        if (skillsComponent != null) {
            int level = skillsComponent.getSkillLevel(new ResourceUrn(multipliesTimeWithSkillComponent.skillUrn));
            if (level > 0) {
                event.multiply(((Double) Math.pow(multipliesTimeWithSkillComponent.multiplier, level)).floatValue());
            }
        }
    }
}
