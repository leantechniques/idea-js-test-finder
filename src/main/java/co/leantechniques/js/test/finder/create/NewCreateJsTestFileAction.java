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

import co.leantechniques.js.test.finder.PluginBundle;
import co.leantechniques.js.test.finder.ui.DialogBuilder;
import co.leantechniques.js.test.finder.ui.DisplayDialogAction;
import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateFileAction;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import javax.swing.Icon;

public class NewCreateJsTestFileAction extends DisplayDialogAction<CreateTestFileDialog> implements DumbAware {
    private static final Icon ICON = StdFileTypes.JS.getIcon();
    private TestFileTemplateLocator testFileTemplateLocator = new TestFileTemplateLocator();
    private GenerateFileFromTemplate generateFileFromTemplate = new GenerateFileFromTemplate();
    private ProjectDirectoryMaker projectDirectoryMaker = new ProjectDirectoryMaker();

    public NewCreateJsTestFileAction() {
        super(
                PluginBundle.message("configurator.create.test.action.name"),
                PluginBundle.message("configurator.create.test.action.description"),
                ICON
        );
    }

    @Override
    protected DialogBuilder constructDialog(AnActionEvent event) {
        DataContext dataContext = event.getDataContext();
        String directoryName = suggestedDirectoryPath(dataContext);
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        String suggestedFilename = CreateJsTestFileActionInvoker.SUGGESTED_FILENAME.getData(dataContext);

        GenerateFileFromTemplateDialogBuilder builder = new GenerateFileFromTemplateDialogBuilder(project);
        builder.withTitle(PluginBundle.message("configurator.create.test.dialog.title"));
        builder.withDestinationDirectory(directoryName);
        builder.withFilename(suggestedFilename);
        builder.withValidator(new CreateTestFileDialogValidator());

        for (FileTemplate testTemplate : testFileTemplateLocator.locate(project)) {
            builder.withTemplate(testTemplate, ICON);
        }

        return builder;
    }

    @Override
    protected void handleOkClicked(CreateTestFileDialog wrapper, DataContext dataContext) {
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        String destinationDirectory = wrapper.getDestinationDirectory();
        String filename = wrapper.getFilename();

        VirtualFile dir = projectDirectoryMaker.make(project, destinationDirectory);
        PsiDirectory psiDirectory = PsiManager.getInstance(project).findDirectory(dir);
        CreateFileAction.MkDirs mkdirs = new CreateFileAction.MkDirs(filename, psiDirectory);
        psiDirectory = mkdirs.directory;

        FileTemplate template = testFileTemplateLocator.locateByName(project, wrapper.getFileTemplateName());
        PsiFile psiFile = generateFileFromTemplate.generate(template, filename, psiDirectory);

//        if (template.isLiveTemplateEnabled()) {
//            CreateFromTemplateActionBase.startLiveTemplate(psiFile);
//        }

        FileEditorManager.getInstance(project).openFile(psiFile.getVirtualFile(), true);

    }

    private String suggestedDirectoryPath(DataContext dataContext) {
        String suggestedDirectory = CreateJsTestFileActionInvoker.SUGGESTED_DIRECTORY.getData(dataContext);
        if (suggestedDirectory == null || suggestedDirectory.isEmpty()) {
            IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
            PsiDirectory dir = view.getOrChooseDirectory();
            return dir.getName();
        }
        return suggestedDirectory;
    }

}
