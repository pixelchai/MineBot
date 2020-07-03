import gym
import minerl
# minerl.data.download("data",experiment='MineRLObtainDiamond-v0')

if __name__ == '__main__':
    # Run a random agent through the environment
    env = gym.make("MineRLObtainDiamond-v0") # A MineRLObtainDiamond-v0 env

    obs = env.reset()
    done = False

    while not done:
        # Take a no-op through the environment.
        obs, rew, done, _ = env.step(env.action_space.noop())
        # Do something
