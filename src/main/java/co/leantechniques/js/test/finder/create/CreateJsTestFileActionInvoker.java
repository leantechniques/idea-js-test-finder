package co.leantechniques.js.test.finder.create;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

// todo - this just feels hacky...
public class CreateJsTestFileActionInvoker {
    private DirectoriesMarkedAsTestLocator directoriesMarkedAsTestLocator = new DirectoriesMarkedAsTestLocator();

    public void invoke(Project project, PsiFile fileToCreateTestFor) {
        CreateJsTestFileAction action = new CreateJsTestFileAction();

        SimpleDataContext dataContext = new SimpleDataContext();
        dataContext.add(CommonDataKeys.PROJECT, project);

        List<VirtualFile> testDirectories = directoriesMarkedAsTestLocator.locateForModuleThatContains(fileToCreateTestFor);
        if (testDirectories.size() > 0) {
            VirtualFile dir = testDirectories.get(0);
            PsiDirectory psiDir = PsiManager.getInstance(project).findDirectory(dir);
            dataContext.add(LangDataKeys.IDE_VIEW, new SimpleIdeView(psiDir));
        }

        AnActionEvent event = new AnActionEvent(null, dataContext, ActionPlaces.UNKNOWN, new Presentation(""), ActionManager.getInstance(), 0);
        action.actionPerformed(event);
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
