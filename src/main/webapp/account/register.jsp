<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="../assets/fonts/stylesheet.css">

    <link rel="stylesheet" href="../assets/css/reset.css">
    <link rel="stylesheet" href="../assets/css/authentication.css">
    <title>Sign In</title>
</head>
<body>
    <div class="form__block">
        <h1 class="form__title">Detective Paimon</h1>
        <p class="form__desc">Please enter your user information.</p>
        
        <form class="form__sign-in" action="./register" method="post">
            <div class="form__group">
                <label for="username" class="form__label">Username</label>
                <input type="text" id="username" name="username" class="form__input" required placeholder="Enter username">
            </div>
            <div class="form__group">
                <label for="email" class="form__label">Email</label>
                <input type="email" id="email" name="email" class="form__input" required placeholder="Enter your email">
            </div>
            <div class="form__group">
                <label for="password" class="form__label">Password</label>
                <input type="password" id="password" name="password" class="form__input" required placeholder="********">
            </div>
            <div class="form__group">
                <label for="confirmPassword" class="form__label">Comfirm password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form__input" required placeholder="********">
            </div>
            <div class="form__group">
                <label for="accept_rules" class="form__label form__label--checkbox">
                    <input type="checkbox" id="accept_rules" name="accept_rules" class="form__input--checkbox">
                    I agree to the Terms of Service and Privacy Policy.
                </label>
            </div>
            <div class="form__group">
                <button type="submit" class="form__button">Create an account</button>
            </div>
        </form>

        <div class="form__row">
            <a href="./login" class="form__link form-link__login-acc">Already member? Login</a>
            <a href="forget-password.html" class="form__link">Forgot your password?</a>     
        </div>
    </div>
    
</body>
</html>