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

import co.leantechniques.js.test.finder.ui.DialogInputValidator;
import com.google.common.base.Optional;
import com.intellij.ide.actions.TemplateKindCombo;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.util.PlatformIcons;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.ArrayList;

public class CreateTestFileDialog extends DialogWrapper {
    private final Project project;
    private final ArrayList<TemplateData> templates;
    private JTextField filename;
    private JTextField destinationDirectory;
    private TemplateKindCombo templateSelection;
    private JPanel panel;
    private Optional<DialogInputValidator> validator = Optional.absent();

    protected CreateTestFileDialog(@Nullable Project project, ArrayList<TemplateData> templates) {
        super(project, true);
        this.project = project;
        this.templates = templates;

        filename = new JTextField();
        destinationDirectory = new JTextField();

        templateSelection = new TemplateKindCombo();
        templateSelection.registerUpDownHint(filename);

        for (TemplateData template : templates) {
            templateSelection.addItem(template.fileTemplate.getName(), template.icon, template.fileTemplate.getName());
        }

        JLabel templateSelectionLabel = new JLabel();
        templateSelectionLabel.setLabelFor(templateSelection);

        JLabel upDownHint = new JLabel();
        upDownHint.setIcon(PlatformIcons.UP_DOWN_ARROWS);

        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
                "right:pref, 3dlu pref:grow, 3dlu, pref",
                "pref, 3dlu, pref, 3dlu, pref"
        );

        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        builder.addLabel("Filename:", cc.xy(1, 1));
        builder.add(filename, cc.xyw(3, 1, 3));
        builder.addLabel("Destination:", cc.xy(1, 3));
        builder.add(destinationDirectory, cc.xyw(3, 3, 3));

        if (templates.size() > 1) {
            builder.add(templateSelectionLabel, cc.xy(1, 5));
            builder.add(templateSelection, cc.xy(3, 5));
            builder.add(upDownHint, cc.xy(5, 5));
        }

        panel = builder.getPanel();

        init();
    }

    public String getFilename() {
        return this.filename.getText();
    }

    public void setFilename(String filename) {
        if (filename != null) {
            this.filename.setText(filename);
        }
    }

    public String getDestinationDirectory() {
        return destinationDirectory.getText();
    }

    public void setDestinationDirectory(String name) {
        this.destinationDirectory.setText(name);
    }

    public String getFileTemplateName() {
        return templateSelection.getSelectedName();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (validator.isPresent()) {
            Optional<ValidationInfo> results = validator.get().validate(project, this);
            if (results.isPresent()) {
                return results.get();
            }
        }
        return super.doValidate();
    }

    public void setValidator(DialogInputValidator validator) {
        this.validator = Optional.fromNullable(validator);
    }

    public JComponent getFilenameComponent() {
        return filename;
    }

    public String getFilenameExtension() {
        return getFileTemplate().getExtension();
    }

    public FileTemplate getFileTemplate() {
        String selectedTemplateName = templateSelection.getSelectedName();
        for (TemplateData template : templates) {
            if (template.fileTemplate.getName().equals(selectedTemplateName)) {
                return template.fileTemplate;
            }
        }
        return null;
    }

    public static class TemplateData {
        private final FileTemplate fileTemplate;
        private final Icon icon;

        public TemplateData(FileTemplate template, Icon icon) {
            this.fileTemplate = template;
            this.icon = icon;
        }
    }
}
