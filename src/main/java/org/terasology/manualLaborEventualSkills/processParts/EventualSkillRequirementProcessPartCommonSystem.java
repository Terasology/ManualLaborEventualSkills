/*
 * Copyright 2016 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.manualLaborEventualSkills.processParts;

import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.registry.In;
import org.terasology.eventualSkills.components.EntitySkillsComponent;
import org.terasology.eventualSkills.systems.EventualSkillsManager;
import org.terasology.eventualSkills.ui.EventualSkillsUIUtil;
import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.gestalt.entitysystem.event.ReceiveEvent;
import org.terasology.nui.UIWidget;
import org.terasology.workstation.process.ProcessPartDescription;
import org.terasology.workstation.processPart.ProcessEntityIsInvalidToStartEvent;
import org.terasology.workstation.processPart.metadata.ProcessEntityGetInputDescriptionEvent;

@RegisterSystem
public class EventualSkillRequirementProcessPartCommonSystem extends BaseComponentSystem {
    @In
    EventualSkillsManager skillsManager;

    ///// Processing
    @ReceiveEvent
    public void validateToStartExecution(ProcessEntityIsInvalidToStartEvent event, EntityRef processEntity,
                                         EventualSkillRequirementComponent eventualSkillRequirementComponent) {
        if (event.getInstigator().equals(event.getWorkstation())) {
            // let auto processing machines continue
            return;
        }

        EntitySkillsComponent skills = event.getInstigator().getComponent(EntitySkillsComponent.class);
        if (skills != null) {
            if (skills.hasSkill(new ResourceUrn(eventualSkillRequirementComponent.skillUrn), eventualSkillRequirementComponent.level)) {
                return;
            }
        }
        event.consume();
    }

    ///// Metadata

    @ReceiveEvent
    public void getInputDescriptions(ProcessEntityGetInputDescriptionEvent event, EntityRef processEntity,
                                     EventualSkillRequirementComponent eventualSkillRequirementComponent) {
        String skillDescription = skillsManager.getSkill(new ResourceUrn(eventualSkillRequirementComponent.skillUrn)).name + " " + eventualSkillRequirementComponent.level;
        UIWidget widget = EventualSkillsUIUtil.createEventualSkillsIcon(new ResourceUrn(eventualSkillRequirementComponent.skillUrn), eventualSkillRequirementComponent.level);
        widget.setTooltip(skillDescription);
        event.addInputDescription(new ProcessPartDescription(null, skillDescription, widget));
    }
}
