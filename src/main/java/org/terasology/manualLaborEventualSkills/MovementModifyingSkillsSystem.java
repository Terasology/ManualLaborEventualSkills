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
package org.terasology.manualLaborEventualSkills;

import org.terasology.assets.ResourceUrn;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.eventualSkills.components.EntityEventualSkillsComponent;
import org.terasology.logic.characters.GetMaxSpeedEvent;

@RegisterSystem
public class MovementModifyingSkillsSystem extends BaseComponentSystem {
    @ReceiveEvent
    public void modifySpeed(GetMaxSpeedEvent event, EntityRef entityRef, EntityEventualSkillsComponent skillsComponent) {
        switch (event.getMovementMode()) {
            case CLIMBING:
            case SWIMMING:
            case WALKING:
                event.multiply(1f + (0.05f * skillsComponent.getSkillLevel(new ResourceUrn("ManualLaborEventualSkills:FitnessSkill"))));
        }

        switch (event.getMovementMode()) {
            case SWIMMING:
                event.multiply(1f + (0.1f * skillsComponent.getSkillLevel(new ResourceUrn("ManualLaborEventualSkills:SwimmingSkill"))));
                break;
            case WALKING:
                event.multiply(1f + (0.1f * skillsComponent.getSkillLevel(new ResourceUrn("ManualLaborEventualSkills:RunningSkill"))));
                break;

        }
    }
}
