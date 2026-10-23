<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Đăng ký</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>
<section class="auth-page">
    <div class="auth-card">
        <div class="auth-form-wrap">
            <div class="auth-head">
                <h1>Đăng ký</h1>
                <p>Tạo tài khoản mới để bắt đầu</p>
            </div>

            <form class="auth-form" action="${pageContext.request.contextPath}/register" method="post">
                <div class="auth-field">
                    <label for="fullName">Họ và tên</label>
                    <input type="text" id="fullName" name="fullName" placeholder="Nguyễn Văn A" autocomplete="name" required>
                </div>

                <div class="auth-field">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="example@gmail.com" autocomplete="email" required>
                </div>

                <div class="auth-field">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" placeholder="••••••••" autocomplete="new-password" required>
                </div>

                <button type="submit" class="auth-submit">Sign up</button>
            </form>

            <div class="auth-divider"><span>Hoặc đăng ký với</span></div>

            <div class="auth-social">
                <a class="auth-social-btn" href="${pageContext.request.contextPath}/oauth2/authorization/google">
                    <svg viewBox="0 0 24 24" width="20" height="20">
                        <path fill="#4285F4" d="M23.5 12.3c0-.8-.1-1.6-.2-2.3H12v4.5h6.5a5.6 5.6 0 0 1-2.4 3.6v3h3.9c2.2-2.1 3.5-5.1 3.5-8.8z"/>
                        <path fill="#34A853" d="M12 24c3.2 0 6-1.1 8-2.9l-3.9-3c-1.1.7-2.5 1.2-4.1 1.2-3.1 0-5.8-2.1-6.7-5H1.3v3.1A12 12 0 0 0 12 24z"/>
                        <path fill="#FBBC05" d="M5.3 14.3a7.2 7.2 0 0 1 0-4.6V6.6h-4a12 12 0 0 0 0 10.8l4-3.1z"/>
                        <path fill="#EA4335" d="M12 4.8c1.8 0 3.3.6 4.6 1.8l3.4-3.4A12 12 0 0 0 1.3 6.6l4 3.1c.9-2.9 3.6-4.9 6.7-4.9z"/>
                    </svg>
                    Google
                </a>
                <a class="auth-social-btn" href="${pageContext.request.contextPath}/oauth2/authorization/facebook">
                    <svg viewBox="0 0 24 24" width="20" height="20">
                        <path fill="#1877F2" d="M24 12a12 12 0 1 0-13.9 11.9v-8.4h-3V12h3V9.4c0-3 1.8-4.7 4.5-4.7 1.3 0 2.7.2 2.7.2v3h-1.5c-1.5 0-2 .9-2 1.9V12h3.4l-.5 3.5h-2.9v8.4A12 12 0 0 0 24 12z"/>
                    </svg>
                    Facebook
                </a>
            </div>

            <p class="auth-switch">
                Đã có tài khoản?
                <a class="auth-link auth-link--strong" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </p>
        </div>
    </div>
</section>
</body>
</html>
