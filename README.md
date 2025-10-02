testbattle
# 1️⃣ Send OTP
curl -X GET "http://localhost:8080/api/otp/send?email=akash.unknow@gmail.com"

# 2️⃣ Verify OTP
curl -X POST "http://localhost:8080/api/otp/verify?email=akash.unknow@gmail.com&otp=235876"

# 3️⃣ Register User
curl -X POST "http://localhost:8080/api/otp/register" \
-H "Content-Type: application/json" \
-d '{
  "fname": "akaa",
  "lname": "Dev",
  "dob": "1995-10-01",
  "email": "akash.ad@example.com",
  "password": "MySecurePassword123"
}'

# 4️⃣ Login with Email/Password
curl -X POST "http://localhost:8080/api/otp/login" \
-H "Content-Type: application/json" \
-d '{
  "email": "akash.ad@example.com",
  "password": "MySecurePassword123"
}'

# 5️⃣ Google OAuth2 Login
# Open this URL in browser to trigger Google login (cannot be done via curl)
echo "Open in browser: http://localhost:8080/api/otp/login/google"
