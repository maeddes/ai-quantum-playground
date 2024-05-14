from qiskit import QuantumCircuit
from qiskit_ibm_runtime import QiskitRuntimeService, SamplerV2 as Sampler
from qiskit import QuantumRegister, QuantumCircuit 
from qiskit.circuit import ClassicalRegister

 
# You'll need to specify the credentials when initializing QiskitRuntimeService, if they were not previously saved.
service = QiskitRuntimeService()
backend = service.backend("ibmq_qasm_simulator")
# backend = service.backend("ibm_brisbane")
# backend = service.backend("ibm_kyoto")
# backend = service.backend("ibm_sherbrooke")
# backend = service.backend("ibm_osaka")

size = 7
qr = QuantumRegister(size) # Create a quantum register of size n
cr = ClassicalRegister(size)
circuit = QuantumCircuit(qr, cr)
circuit.h(qr) # Apply Hadamard gate to qubits
circuit.measure(qr, cr)

job = backend.run(circuit, shots=100, memory=True)
result = job.result()
print(result)

data = job.result().get_memory()
print(data)

counts = result.get_counts()

int_data = []
for bitstring in data:
    int_data.append( int(bitstring,2) )
print(int_data)

#print("Counts:", counts)
#bitstring = next(iter(counts.keys())) 