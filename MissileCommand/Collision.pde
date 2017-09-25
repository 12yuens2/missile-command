public class Collision {

    private Particle p1, p2;
    
    private float c;
    
    private PVector contactNormal;
    
    public Collision(Particle p1, Particle p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.c = 0.999f;
        
        this.contactNormal = PVector.sub(p2.position,  p1.position).normalize();
    }
    
    public void resolveCollision() {
        float closingVel = calculateClosingVelocity();
        
        float deltaVel = (-closingVel * c) - closingVel;
        
        float impulse = deltaVel / ((1f/p1.mass) + (1f/p2.mass));        
        if (abs(closingVel) < 0.1f) {
            impulse = impulse * 1000f;
        }
        
        PVector impulsePerInverseMass = contactNormal.get().mult(impulse);
        
        PVector p1Impulse = impulsePerInverseMass.get().mult((1f/p1.mass));
        PVector p2Impulse = impulsePerInverseMass.get().mult(-(1f/p2.mass));
        
        //if (PVector.dot(p1.velocity, p2.velocity) <= 0.1f) {
            p1.velocity.add(p1Impulse);
            p2.velocity.add(p2Impulse);
            //p1.integrate(p1Impulse.mult(1/2f));
            //p2.integrate(p2Impulse.mult(1/2f));
        //}
    }
    
    public float calculateClosingVelocity() {
        float cv1 = p1.velocity.dot(PVector.sub(p2.position, p1.position).normalize());
        float cv2 = p2.velocity.dot(PVector.sub(p1.position, p2.position).normalize());
        
        float closingVel = cv1 + cv2;
        
        return closingVel;
    }
    
    
}