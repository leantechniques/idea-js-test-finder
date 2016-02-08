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
package co.leantechniques.js.test.finder.ui;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public abstract class DisplayDialogAction<T extends DialogWrapper> extends AnAction {
    public DisplayDialogAction(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
        super(text, description, icon);
    }

    @Override
    public final void actionPerformed(AnActionEvent event) {
        DataContext dataContext = event.getDataContext();

        if (isSafeToDisplayTheDialog(dataContext)) {
            T dialogWrapper = (T) constructDialog(event).build();
            dialogWrapper.show();
            if (dialogWrapper.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
                handleOkClicked(dialogWrapper, dataContext);
            } else {
                handleCancelledOrClosedClicked(dialogWrapper, dataContext);
            }
        }
    }

    protected abstract DialogBuilder constructDialog(AnActionEvent event);

    protected void handleOkClicked(T wrapper, DataContext dataContext) {

    }

    protected void handleCancelledOrClosedClicked(T wrapper, DataContext dataContext) {

    }

    private boolean isSafeToDisplayTheDialog(DataContext dataContext) {
        IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        return view != null && project != null;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().isInstance(obj);
    }
}
