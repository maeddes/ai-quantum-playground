
# Replace YOUR_API_TOKEN with your actual IBM Quantum API token
from qiskit_ibm_runtime import QiskitRuntimeService
 
# Save an IBM Quantum account and set it as your default account.
QiskitRuntimeService.save_account(channel="ibm_quantum", token="25a203dc95959329d5d6cd76a5d6d5c20941cb1963d519bcd5a25968001ac0204617942678989c60fa9f016b42f4ee80ea1b6b5c00c2bafa861aad3e3b207449", set_as_default=True, overwrite=True)
 
# Load saved credentials
service = QiskitRuntimeService()