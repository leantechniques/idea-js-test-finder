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

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

// todo - this just feels hacky...
public class CreateJsTestFileActionInvoker {
    public static final DataKey<String> SUGGESTED_FILENAME = DataKey.create("suggested-filename");
    public static final DataKey<String> SUGGESTED_DIRECTORY = DataKey.create("suggested-directory");

    public void invoke(Project project, PsiFile fileToCreateTestFor) {
        SimpleDataContext dataContext = new SimpleDataContext();
        dataContext.add(CommonDataKeys.PROJECT, project);
        dataContext.add(SUGGESTED_FILENAME, determineSuggestedFilename(fileToCreateTestFor));
        dataContext.add(SUGGESTED_DIRECTORY, determineSuggestedDirectory(fileToCreateTestFor));
        dataContext.add(LangDataKeys.IDE_VIEW, new SimpleIdeView(fileToCreateTestFor.getParent()));

        AnActionEvent event = new AnActionEvent(null, dataContext, ActionPlaces.UNKNOWN, new Presentation(""), ActionManager.getInstance(), 0);

        AnAction action = new NewCreateJsTestFileAction();
        action.actionPerformed(event);
    }

    private String determineSuggestedDirectory(PsiFile fileToCreateTestFor) {
        // TODO - can this be used in conjuction with the `test` folder locator to help determine what folder should be used?
        String projectPath = fileToCreateTestFor.getProject().getBasePath();
        String fileToBeTestedPath = fileToCreateTestFor.getContainingFile().getVirtualFile().getPath();
        String potentialPath = fileToBeTestedPath.replace(projectPath, "").replace(fileToCreateTestFor.getName(), "");
        potentialPath = potentialPath.replaceFirst("^/(.+?)(/.*)/", "test$2");
        return potentialPath;
    }

    @NotNull
    private String determineSuggestedFilename(PsiFile fileToCreateTestFor) {
        String name = fileToCreateTestFor.getName();
        return name.substring(0, name.lastIndexOf("."));
    }

    private class SimpleDataContext implements DataContext {
        private HashMap<String, Object> data = new HashMap<String, Object>();

        @Nullable
        @Override
        public Object getData(@NonNls String key) {
            return data.get(key);
        }

        public void add(DataKey key, Object value) {
            data.put(key.getName(), value);
        }
    }

    private class SimpleIdeView implements IdeView {
        private final PsiDirectory dir;

        public SimpleIdeView(PsiDirectory dir) {
            this.dir = dir;
        }

        @Override
        public void selectElement(PsiElement psiElement) {

        }

        @NotNull
        @Override
        public PsiDirectory[] getDirectories() {
            return new PsiDirectory[]{getOrChooseDirectory()};
        }

        @Nullable
        @Override
        public PsiDirectory getOrChooseDirectory() {
            return dir;
        }
    }

}
