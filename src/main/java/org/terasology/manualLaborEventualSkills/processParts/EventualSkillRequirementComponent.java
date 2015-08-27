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
package org.terasology.manualLaborEventualSkills.processParts;

import com.google.common.collect.Lists;
import org.terasology.assets.ResourceUrn;
import org.terasology.entitySystem.Component;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.eventualSkills.components.EntityEventualSkillsComponent;
import org.terasology.eventualSkills.systems.EventualSkillsManager;
import org.terasology.eventualSkills.ui.EventualSkillsUIUtil;
import org.terasology.registry.CoreRegistry;
import org.terasology.rendering.nui.UIWidget;
import org.terasology.workstation.process.DescribeProcess;
import org.terasology.workstation.process.ProcessPart;
import org.terasology.workstation.process.ProcessPartDescription;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EventualSkillRequirementComponent implements Component, ProcessPart, DescribeProcess {
    public String skillUrn;
    public int level;

    @Override
    public boolean validateBeforeStart(EntityRef instigator, EntityRef workstation, EntityRef processEntity) {
        if (instigator.equals(workstation)) {
            // let auto processing machines continue
            return true;
        }

        EntityEventualSkillsComponent skills = instigator.getComponent(EntityEventualSkillsComponent.class);
        if (skills != null) {
            return skills.hasSkill(new ResourceUrn(skillUrn), level);
        }
        return false;
    }

    @Override
    public long getDuration(EntityRef instigator, EntityRef workstation, EntityRef processEntity) {
        return 0;
    }

    @Override
    public void executeStart(EntityRef instigator, EntityRef workstation, EntityRef processEntity) {

    }

    @Override
    public void executeEnd(EntityRef instigator, EntityRef workstation, EntityRef processEntity) {

    }

    @Override
    public Collection<ProcessPartDescription> getOutputDescriptions() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ProcessPartDescription> getInputDescriptions() {
        List<ProcessPartDescription> descriptions = Lists.newLinkedList();
        EventualSkillsManager skillsManager = CoreRegistry.get(EventualSkillsManager.class);
        String skillDescription = skillsManager.getSkill(new ResourceUrn(skillUrn)).name + " " + level;
        UIWidget widget = EventualSkillsUIUtil.createEventualSkillsIcon(new ResourceUrn(skillUrn));
        widget.setTooltip(skillDescription);
        descriptions.add(new ProcessPartDescription(null, skillDescription, widget));
        return descriptions;
    }
}
