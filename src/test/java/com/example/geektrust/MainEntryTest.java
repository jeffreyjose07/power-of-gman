package com.example.geektrust;

import org.junit.jupiter.api.Test;

import com.example.geektrust.ApplicationError;
import java.io.File;
import java.io.FileWriter;
import static org.junit.jupiter.api.Assertions.*;

public class MainEntryTest {
    private static class ExitSecurityManager extends SecurityManager {
        private Integer status = null;
        @Override
        public void checkPermission(java.security.Permission perm) {}
        @Override
        public void checkExit(int status) {
            this.status = status;
            throw new SecurityException();
        }
    }

    @Test
    public void testNoArgs() {
        SecurityManager original = System.getSecurityManager();
        ExitSecurityManager sm = new ExitSecurityManager();
        System.setSecurityManager(sm);
        try {
            assertThrows(SecurityException.class, () -> Main.main(new String[]{}));
            assertEquals(ApplicationError.NO_INPUT.getExitCode(), sm.status);
        } finally {
            System.setSecurityManager(original);
        }
    }

    @Test
    public void testFileNotFound() {
        SecurityManager original = System.getSecurityManager();
        ExitSecurityManager sm = new ExitSecurityManager();
        System.setSecurityManager(sm);
        try {
            assertThrows(SecurityException.class, () -> Main.main(new String[]{"nofile.txt"}));
            assertEquals(ApplicationError.FILE_NOT_FOUND.getExitCode(), sm.status);
        } finally {
            System.setSecurityManager(original);
        }
    }

    @Test
    public void testValidFile() throws Exception {
        File temp = File.createTempFile("input", ".txt");
        try (FileWriter w = new FileWriter(temp)) {
            w.write("PRINT_POWER\n");
        }
        SecurityManager original = System.getSecurityManager();
        ExitSecurityManager sm = new ExitSecurityManager();
        System.setSecurityManager(sm);
        try {
            // Should not call exit, so no exception
            assertDoesNotThrow(() -> Main.main(new String[]{temp.getAbsolutePath()}));
            assertNull(sm.status);
        } finally {
            System.setSecurityManager(original);
        }
    }
}
