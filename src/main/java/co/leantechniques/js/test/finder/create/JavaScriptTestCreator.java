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
import co.leantechniques.js.test.finder.PluginBundle;
import co.leantechniques.js.test.finder.ServiceManagerProxy;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testIntegration.TestCreator;
import org.jetbrains.annotations.NotNull;

public class JavaScriptTestCreator implements TestCreator {
    private static final NotificationGroup NOTIFICATIONS = new NotificationGroup(JavaScriptTestCreator.class.getName(), NotificationDisplayType.STICKY_BALLOON, true);
    private TestFileTemplateLocator testFileTemplateLocator = new TestFileTemplateLocator();
    private ServiceManagerProxy serviceManagerProxy = new ServiceManagerProxy();
    private CreateJsTestFileActionInvoker createJsTestFileActionInvoker = new CreateJsTestFileActionInvoker();

    @Override
    public boolean isAvailable(Project project, Editor editor, PsiFile psiFile) {
        return filenameExtensionFactory(psiFile).isProductionFile(psiFile);
    }

    @Override
    public void createTest(Project project, Editor editor, PsiFile file) {
        // todo - are there reasonable defaults?
        if (testFileTemplateLocator.hasNoTestTemplates(project)) {
            notification(project, PluginBundle.message("configurator.create.test.no.test.templates.found"));
            return;
        }

        if (isAvailable(project, editor, file)) {
            createJsTestFileActionInvoker.invoke(project, file);
        }
    }

    private void notification(final Project project, final String message) {
        // TODO - look at `MavenImportNotifier.java:92` and determine how that stuff works
        NOTIFICATIONS.createNotification(message, NotificationType.WARNING).notify(project);
    }

    private FilenameExtensionFactory filenameExtensionFactory(@NotNull PsiElement psiElement) {
        return serviceManagerProxy.get(psiElement.getProject(), FilenameExtensionFactory.class);
    }



}
