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
