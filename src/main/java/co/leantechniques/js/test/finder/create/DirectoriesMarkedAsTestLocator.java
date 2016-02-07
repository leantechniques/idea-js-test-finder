package co.leantechniques.js.test.finder.create;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoriesMarkedAsTestLocator {
    public List<VirtualFile> locateForModuleThatContains(PsiFile file) {
        Module module = ModuleUtilCore.findModuleForPsiElement(file);
        ModuleRootManager manager = ModuleRootManager.getInstance(module);
        List<VirtualFile> allSourceRoots = new ArrayList(Arrays.asList(manager.getSourceRoots()));
        allSourceRoots.removeAll(Arrays.asList(manager.getSourceRoots(false)));
        return allSourceRoots;
    }
}
