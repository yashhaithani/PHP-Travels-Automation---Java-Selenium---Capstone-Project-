# 🚀 Capstone Project – Selenium Automation Testing on PHP Travels  

## 👨‍💻 About Me  
**Name:** Yash Aithani  
**Superset ID:** 3427235  
**Batch:** Java Selenium – Batch 4  

---

## 📌 Project Overview  
This project is a **Capstone Automation Framework** built using **Java & Selenium WebDriver** to test the **PHP Travels demo website**, a travel booking platform.  

The framework automates **end-to-end user workflows** including:  
- 📝 Registration / Signup  
- ✈️ Flight Booking  
- 💳 Payment Gateway Validation  

It uses **BDD (Cucumber)** for writing test cases in plain English and follows the **Page Object Model (POM)** for better maintainability and scalability.  

---

## 🛠️ Tech Stack  
- **Programming Language:** Java 21  
- **Automation Tool:** Selenium WebDriver 4.8.3  
- **BDD Framework:** Cucumber  
- **Test Execution & Assertions:** TestNG  
- **Driver Management:** WebDriverManager  
- **Build Tool:** Maven  

---

## ✅ Test Scenarios Implemented  

### 🔹 Registration / Signup  
- **Positive Case:** Valid user data with India as country.  
- **Negative Case:** Missing/invalid details (empty email, weak password).  
- ⚠️ CAPTCHA handling skipped in test automation (workaround in test environment).  

### 🔹 Flight Booking  
- **Positive Case:** Search flight (DEL → BLR), book first available flight, fill passenger details, confirm booking, verify confirmation.  
- **Negative Case:** Invalid inputs (wrong airport codes, missing traveler details).  

### 🔹 Payment Gateway  
- **Negative Case:** Invalid card details → Validate error messages.  

---

## 📂 Framework Structure  
- **Feature Files** → Business-readable test scenarios (`.feature`)  
- **Step Definitions** → Maps feature steps to Java methods  
- **Page Objects** → Encapsulates locators & actions (SignupPage, FlightSearchPage, FlightBookingPage)  
- **Utilities** → Config reader, waits, element wrappers  
- **DriverFactory** → Centralized WebDriver setup  

---

## ⚡ Challenges & Solutions  
- **CAPTCHA** → Skipped / bypassed for test environment.  
- **Loader (#preloader)** → Handled with `WebDriverWait` until invisible.  
- **Destination field not filling** → Fixed using explicit waits & key events.  
- **Flaky UI elements** → Implemented retry + refresh logic.  

---

## 🏆 Outcome  
The project successfully automated:  
- ✅ User Registration  
- ✅ Flight Booking (positive & negative)  
- ✅ Payment Gateway (negative validation)  

**Key Deliverables:**  
- 📌 Maintainable framework using **POM**  
- 📌 Readable scenarios with **BDD (Cucumber)**  
- 📌 Robust error handling & real-world automation strategies  

---

## 🎯 End Goal  
To showcase strong expertise in:  
- Selenium with Java  
- BDD with Cucumber  
- POM framework design  
- Handling real-world automation challenges (loaders, CAPTCHAs, flaky elements)  

---

## 📊 Suggested Enhancements (Future Scope)  
- Add **CI/CD Integration** with Jenkins or GitHub Actions  
- Extend framework for **API testing** alongside UI  
- Enable **cross-browser & parallel execution** using Selenium Grid or Docker  

---

## 📸 Presentation Suggestions  
- Flow diagram: **Feature → Step Definitions → Pages → Driver**  
- Workflow: **Signup → Flight Search → Booking → Payment**  
- Minimal text + bullet points for clarity  

---

✨ *This Capstone Project demonstrates my ability to build a scalable, production-ready automation testing framework while tackling real-world challenges in web automation.*  
