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

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.util.Properties;

public class GenerateFileFromTemplate {
    public PsiFile generate(FileTemplate template, String filename, PsiDirectory directory) {
        try {
            Project project = directory.getProject();
            Properties properties = FileTemplateManager.getInstance(project).getDefaultProperties();
            PsiElement element = FileTemplateUtil.createFromTemplate(template, filename, properties, directory);
            return element.getContainingFile();
        } catch (Exception e) {
            throw new FailedToGenerateFileFromTemplate(template, filename, directory, e);
        }
    }

    private class FailedToGenerateFileFromTemplate extends RuntimeException {
        public FailedToGenerateFileFromTemplate(FileTemplate template, String filename, PsiDirectory directory, Exception cause) {
            super("Failed to generate file.\n" +
                            "template: " + template.getName() + "\n" +
                            "filename: " + filename + "\n" +
                            "directory: " + directory.getName() + "\n",
                    cause);
        }
    }
}
