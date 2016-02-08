/**
 * Copyright to the original author or authors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package co.leantechniques.js.test.finder.create;

import co.leantechniques.js.test.finder.ui.DialogInputValidator;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;

import javax.swing.Icon;
import java.util.ArrayList;

public class GenerateFileFromTemplateDialogBuilder implements co.leantechniques.js.test.finder.ui.DialogBuilder {
    private final Project project;
    private String title;
    private ArrayList<TemplateInfo> templates = new ArrayList<TemplateInfo>();
    private String destinationDirectory;
    private String filename;
    private DialogInputValidator validator;

    public GenerateFileFromTemplateDialogBuilder(Project project) {
        this.project = project;
    }

    public GenerateFileFromTemplateDialogBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public GenerateFileFromTemplateDialogBuilder withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public GenerateFileFromTemplateDialogBuilder withTemplate(FileTemplate template, Icon icon) {
        this.templates.add(new TemplateInfo(template, icon));
        return this;
    }

    public GenerateFileFromTemplateDialogBuilder withDestinationDirectory(String directoryName) {
        this.destinationDirectory = directoryName;
        return this;
    }

    public GenerateFileFromTemplateDialogBuilder withValidator(DialogInputValidator validator) {
        this.validator = validator;
        return this;
    }

    @Override
    public DialogWrapper build() {
        ArrayList<CreateTestFileDialog.TemplateData> templateDatas = new ArrayList<CreateTestFileDialog.TemplateData>();
        for (TemplateInfo template : templates) {
            templateDatas.add(new CreateTestFileDialog.TemplateData(template.fileTemplate, template.icon));
        }

        CreateTestFileDialog dialog = new CreateTestFileDialog(project, templateDatas);
        dialog.setTitle(title);
        dialog.setDestinationDirectory(destinationDirectory);
        dialog.setFilename(filename);
        dialog.setValidator(validator);

        return dialog;
    }

    private class TemplateInfo {
        private final FileTemplate fileTemplate;
        private final Icon icon;

        public TemplateInfo(FileTemplate template, Icon icon) {
            this.fileTemplate = template;
            this.icon = icon;
        }
    }
}
