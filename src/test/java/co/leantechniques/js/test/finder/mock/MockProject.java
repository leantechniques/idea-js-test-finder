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
package co.leantechniques.js.test.finder.mock;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.picocontainer.PicoContainer;

public class MockProject implements Project {
    @NotNull
    @Override
    public String getName() {
        return null;
    }

    @Override
    public VirtualFile getBaseDir() {
        return null;
    }

    @Nullable
    @Override
    public String getBasePath() {
        return null;
    }

    @Nullable
    @Override
    public VirtualFile getProjectFile() {
        return null;
    }

    @NotNull
    @Override
    public String getProjectFilePath() {
        return null;
    }

    @Nullable
    @Override
    public String getPresentableUrl() {
        return null;
    }

    @Nullable
    @Override
    public VirtualFile getWorkspaceFile() {
        return null;
    }

    @NotNull
    @Override
    public String getLocationHash() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public BaseComponent getComponent(@NotNull String s) {
        return null;
    }

    @Override
    public <T> T getComponent(@NotNull Class<T> aClass) {
        return null;
    }

    @Override
    public <T> T getComponent(@NotNull Class<T> aClass, T t) {
        return null;
    }

    @Override
    public boolean hasComponent(@NotNull Class aClass) {
        return false;
    }

    @NotNull
    @Override
    public <T> T[] getComponents(@NotNull Class<T> aClass) {
        return null;
    }

    @NotNull
    @Override
    public PicoContainer getPicoContainer() {
        return null;
    }

    @NotNull
    @Override
    public MessageBus getMessageBus() {
        return null;
    }

    @Override
    public boolean isDisposed() {
        return false;
    }

    @NotNull
    @Override
    public <T> T[] getExtensions(@NotNull ExtensionPointName<T> extensionPointName) {
        return null;
    }

    @NotNull
    @Override
    public Condition getDisposed() {
        return null;
    }

    @Override
    public void dispose() {

    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

    }
}
