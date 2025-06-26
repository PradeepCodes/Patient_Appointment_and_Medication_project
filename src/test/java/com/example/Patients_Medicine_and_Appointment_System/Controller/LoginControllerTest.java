import com.example.Patients_Medicine_and_Appointment_System.Controller.LoginController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private final LoginController controller = new LoginController();

    @Test
    void testRedirectByRole_Admin() {
        Authentication auth = mock(Authentication.class);
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // 👇 FIX: Help Mockito resolve generic type
        Mockito.<Collection<? extends GrantedAuthority>>when(auth.getAuthorities()).thenReturn(roles);

        String result = controller.redirectByRole(auth);
        assertEquals("redirect:/admin/dashboard", result);
    }

    @Test
    void testRedirectByRole_Patient() {
        Authentication auth = mock(Authentication.class);
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_PATIENT"));

        Mockito.<Collection<? extends GrantedAuthority>>when(auth.getAuthorities()).thenReturn(roles);

        String result = controller.redirectByRole(auth);
        assertEquals("redirect:/patient/dashboard", result);
    }

    @Test
    void testRedirectByRole_Unknown() {
        Authentication auth = mock(Authentication.class);
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_UNKNOWN"));

        Mockito.<Collection<? extends GrantedAuthority>>when(auth.getAuthorities()).thenReturn(roles);

        String result = controller.redirectByRole(auth);
        assertEquals("redirect:/login?error", result);
    }
}
