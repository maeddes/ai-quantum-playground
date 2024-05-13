# Create empty circuit
# example_circuit = QuantumCircuit(2)
# example_circuit.measure_all()

# qc = QuantumCircuit(2)
# qc.h(0)
# qc.cx(0, 1)
# qc.measure_all()

# sampler = Sampler(backend)
# job = sampler.run([qc])
# print(f"job id: {job.job_id()}")
# result = job.result()
# print(result)

# Create a Quantum Circuit with one qubit
# qc = QuantumCircuit(1)

# # Apply the Hadamard gate
# qc.h(0)

# # Number of shots for measurement (more shots improve randomness)
# shots = 1024
# # Run the circuit on the backend
# job = backend.run(qc, shots=shots)
# # Get the results
# results = job.result()

# print("Results:", results);