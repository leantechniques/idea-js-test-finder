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

import co.leantechniques.js.test.finder.FilenameExtensionFactory;
import co.leantechniques.js.test.finder.PluginBundle;
import co.leantechniques.js.test.finder.ServiceManagerProxy;
import com.intellij.ide.IdeView;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.testIntegration.TestCreator;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class JavaScriptTestCreator implements TestCreator {
    private static final NotificationGroup NOTIFICATIONS = new NotificationGroup(JavaScriptTestCreator.class.getName(), NotificationDisplayType.STICKY_BALLOON, true);
    private TestFileTemplateLocator testFileTemplateLocator = new TestFileTemplateLocator();
    private ServiceManagerProxy serviceManagerProxy = new ServiceManagerProxy();

    @Override
    public boolean isAvailable(Project project, Editor editor, PsiFile psiFile) {
        FilenameExtensionFactory filenameExtensionFactory = filenameExtensionFactory(psiFile);
        Collection<String> prodSuffixes = filenameExtensionFactory.getProductionFileExtensions();
        for (String prodSuffix : prodSuffixes) {
            if (psiFile.getContainingFile().getName().endsWith(prodSuffix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void createTest(Project project, Editor editor, PsiFile file) {
        if (findTestDirectory(file).isEmpty()) {
            notification(project, PluginBundle.message("configurator.create.test.no.test.directory.found"));
            return;
        }

        if (testFileTemplateLocator.locate(project).isEmpty()) {
            notification(project, PluginBundle.message("configurator.create.test.no.test.templates.found"));
            return;
        }

        if (isAvailable(project, editor, file)) {

            CreateJsTestFileAction action = new CreateJsTestFileAction();

            SimpleDataContext dataContext = new SimpleDataContext();
            dataContext.add(CommonDataKeys.PROJECT, project);

            List<VirtualFile> testDirectories = findTestDirectory(file);
            if (testDirectories.size() > 0) {
                VirtualFile dir = testDirectories.get(0);
                PsiDirectory psiDir = PsiManager.getInstance(project).findDirectory(dir);
                dataContext.add(LangDataKeys.IDE_VIEW, new SimpleIdeView(psiDir));
            }

            AnActionEvent event = new AnActionEvent(null, dataContext, ActionPlaces.UNKNOWN, new Presentation(""), ActionManager.getInstance(), 0);
            action.actionPerformed(event);
        }
    }

    private List<VirtualFile> findTestDirectory(PsiFile file) {
        Module module = ModuleUtilCore.findModuleForPsiElement(file);
        ModuleRootManager manager = ModuleRootManager.getInstance(module);
        List<VirtualFile> allSourceRoots = new ArrayList(Arrays.asList(manager.getSourceRoots()));
        allSourceRoots.removeAll(Arrays.asList(manager.getSourceRoots(false)));
        return allSourceRoots;
    }

    private void notification(final Project project, final String message) {
        NOTIFICATIONS.createNotification(message, NotificationType.WARNING).notify(project);
    }

    private FilenameExtensionFactory filenameExtensionFactory(@NotNull PsiElement psiElement) {
        return serviceManagerProxy.get(psiElement.getProject(), FilenameExtensionFactory.class);
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
}
