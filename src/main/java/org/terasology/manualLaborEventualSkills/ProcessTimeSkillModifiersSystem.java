/*
 * Copyright 2015 MovingBlocks
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
package org.terasology.manualLaborEventualSkills;

import org.terasology.asset.Assets;
import org.terasology.assets.ResourceUrn;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.prefab.Prefab;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.eventualSkills.components.EntityEventualSkillsComponent;
import org.terasology.manualLabor.events.ModifyProcessingTimeEvent;
import org.terasology.manualLaborEventualSkills.components.MultipliesTimeWithSkillComponent;
import org.terasology.workstation.component.ProcessDefinitionComponent;

import java.util.Optional;

@RegisterSystem
public class ProcessTimeSkillModifiersSystem extends BaseComponentSystem {
    @ReceiveEvent
    public void onModifyProcessingTime(ModifyProcessingTimeEvent event, EntityRef entityRef, EntityEventualSkillsComponent skillsComponent) {
        ProcessDefinitionComponent processDefinitionComponent = event.getProcessEntity().getComponent(ProcessDefinitionComponent.class);
        if (processDefinitionComponent != null) {
            Optional<Prefab> processDefinition = Assets.getPrefab(processDefinitionComponent.processType);
            if (processDefinition.isPresent()) {
                MultipliesTimeWithSkillComponent multipliesTimeWithSkillComponent = processDefinition.get().getComponent(MultipliesTimeWithSkillComponent.class);
                if (multipliesTimeWithSkillComponent != null) {
                    int level = skillsComponent.getSkillLevel(new ResourceUrn(multipliesTimeWithSkillComponent.skillUrn));
                    if (level > 0) {
                        event.multiply(((Double) Math.pow(multipliesTimeWithSkillComponent.multiplier, level)).floatValue());
                    }
                }
            }
        }
    }
}
