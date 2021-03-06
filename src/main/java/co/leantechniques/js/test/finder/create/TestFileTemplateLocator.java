/**
 *
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package co.leantechniques.js.test.finder.create;

import co.leantechniques.js.test.finder.FilenameExtensionFactory;
import co.leantechniques.js.test.finder.ServiceManagerProxy;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.project.Project;

import java.util.ArrayList;
import java.util.Collection;

public class TestFileTemplateLocator {
    private ServiceManagerProxy serviceManagerProxy = new ServiceManagerProxy();

    public boolean hasNoTestTemplates(Project project) {
        return locate(project).isEmpty();
    }

    public FileTemplate locateByName(Project project, String templateName) {
        for (FileTemplate fileTemplate : locate(project)) {
            if (fileTemplate.getName().equals(templateName)) {
                return fileTemplate;
            }
        }
        throw new IllegalArgumentException("Unable to find template with name:[" + templateName + "]");
    }

    public Collection<FileTemplate> locate(Project project) {
        Collection<String> testFileExtensions = serviceManagerProxy.get(project, FilenameExtensionFactory.class).getTestFileExtensions();

        ArrayList<FileTemplate> testTemplates = new ArrayList<FileTemplate>();
        for (FileTemplate fileTemplate : FileTemplateManager.getInstance(project).getAllTemplates()) {
            if (testFileExtensions.contains("." + fileTemplate.getExtension())) {
                testTemplates.add(fileTemplate);
            }
        }

        return testTemplates;
    }
}
