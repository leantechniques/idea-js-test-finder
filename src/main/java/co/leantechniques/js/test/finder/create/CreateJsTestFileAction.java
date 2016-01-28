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

import co.leantechniques.js.test.finder.PluginBundle;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;

import javax.swing.Icon;
import java.util.Collection;

public class CreateJsTestFileAction extends CreateFileFromTemplateAction implements DumbAware {
    private static final Icon ICON = StdFileTypes.JS.getIcon();
    private TestFileTemplateLocator testFileTemplateLocator = new TestFileTemplateLocator();

    public CreateJsTestFileAction() {
        super(
                PluginBundle.message("configurator.create.test.action.name"),
                PluginBundle.message("configurator.create.test.action.description"),
                ICON
        );
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory psiDirectory, CreateFileFromTemplateDialog.Builder builder) {
        Collection<FileTemplate> testTemplates = testFileTemplateLocator.locate(project);

        builder.setTitle(PluginBundle.message("configurator.create.test.action.name"));
        for (FileTemplate template : testTemplates) {
            builder.addKind(template.getName(), ICON, template.getName());
        }
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s, String s1) {
        return PluginBundle.message("configurator.create.test.action.name");
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CreateJsTestFileAction;
    }

}
