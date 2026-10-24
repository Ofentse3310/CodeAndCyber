cat > README.md <<'EOF'
# Linux Security Log Analyzer

## Overview
A beginner cybersecurity project using Linux commands to investigate synthetic authentication logs.

## Skills Practiced
- Linux terminal navigation
- Searching logs with grep
- Extracting IP addresses
- Counting repeated events with sort and uniq
- Writing a basic security investigation report

## Lab Findings
- Five failed login events were identified.
- 192.168.1.50 generated three failed attempts.
- 192.168.1.60 generated two failed attempts.
- Repeated failures can be suspicious, but do not alone prove an attack.

## Files
- sample_auth.log: synthetic authentication records
- report.txt: written investigation report

## Safety and Scope
All records are synthetic and created for educational purposes.
No external systems were targeted.

## Environment
Ubuntu Linux / WSL
EOF

